package com.stratio.qa

import java.io.File

import org.json4s.Formats

import scala.io.Source
import org.json4s.jackson.Serialization.{read => jacksonRead}


object Utils {

  object fileImplicits extends fileOps

  sealed trait fileOps {

    implicit class FileExt(file: File) {

      // find a file named fileName inside this file (assuming is a folder). None if not found
      def findIfExists(fileName: String): Option[File] =
        Option(file.listFiles)
          .getOrElse(Array.empty)
          .find(_.getName == fileName)

      // find a file named fileName inside this file (assuming is a folder). Throws if not found
      def find(fileName: String, customMsg: Option[String] = None): File = {
        val msg = customMsg
          .getOrElse(s"Cannot find file or folder '$fileName' inside folder '${file.getAbsolutePath}'")
        findIfExists(fileName)
          .getOrElse(throw new RuntimeException(msg))
      }

      // reads this file (should be JSON) and deserializes it to T
      def read[T](implicit formats: Formats, mf: Manifest[T]): T = readAFile(file)

      // finds a file named fileName inside the file (assuming is a folder)
      // and reads it (should be JSON) as T
      def readFile[T](fileName: String)(implicit formats: Formats, mf: Manifest[T]): T =
        readAFile(find(fileName))

      // reads a file with a JSON inside and deserializes it to T
      private def readAFile[T](aFile: File)(implicit formats: Formats, mf: Manifest[T]): T =
        jacksonRead[T](Source.fromFile(aFile).reader)
    }

  }
}
