package utils

import argonaut._, Argonaut._
import spray.http.{ ContentTypes, ContentTypeRange, HttpCharsets, HttpEntity, MediaTypes }
import spray.httpx.marshalling.Marshaller
import spray.httpx.unmarshalling.{ Deserialized, MalformedContent, SimpleUnmarshaller, Unmarshaller }


object ArgonautSupport {
  implicit def argonautMarshallerFromT[T](
    implicit encodeJson: EncodeJson[T],
    prettyPrinter: PrettyParams = PrettyParams.nospace
  ): Marshaller[T] =
    Marshaller.of[T](ContentTypes.`application/json`) { (value, contentType, ctx) =>
      val json = prettyPrinter.pretty(encodeJson.encode(value))
      ctx.marshalTo(HttpEntity(contentType, json))
    }
  /** unmarshall to a T that can be decoded from an Argonaut Json value */
  implicit def argonautUnmarshallerToT[T : DecodeJson]: Unmarshaller[T] =
    delegate[String, T](MediaTypes.`application/json`)(string =>
      string.decodeEither[T].toEither.left.map(e => MalformedContent(e))
    )(UTF8StringUnmarshaller)

  private val UTF8StringUnmarshaller = new Unmarshaller[String] {
    def apply(entity: HttpEntity) = Right(entity.asString(defaultCharset = HttpCharsets.`UTF-8`))
  }

  // Unmarshaller.delegate is used as a kind of map operation; play-json JsResult can contain either validation errors or the JsValue
  // representing a JSON object. We need a delegate method that works as a flatMap and let the provided A => Deserialized[B] function
  // to deal with any possible error, including exceptions.
  //
  private def delegate[A, B](unmarshalFrom: ContentTypeRange*)(f: A => Deserialized[B])(implicit ma: Unmarshaller[A]): Unmarshaller[B] =
    new SimpleUnmarshaller[B] {
      val canUnmarshalFrom = unmarshalFrom
      def unmarshal(entity: HttpEntity) = ma(entity).right.flatMap(a => f(a))
    }
}

