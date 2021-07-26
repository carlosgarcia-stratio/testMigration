package com.stratio.qa

import java.io.File
import java.nio.file.{Files, Path}

import com.stratio.qa.Utils.fileOps
import com.stratio.qa.models.{NewWithAddedFieldWithDefault, NewWithChangedTypes, NewWithDroppedField, NewWithRenamedField, _}
import org.json4s.{DefaultFormats, Formats}
import org.json4s.jackson.Serialization.write
import com.stratio.qa.Utils.fileImplicits._

import scala.util.{Failure, Success, Try}

object App
{

  implicit val json4sJacksonFormats: Formats = DefaultFormats

  def main(args : Array[String]) {

    val dir = "target"
    val fileName = "exported.json"
    val dirFile = new File(dir)

    val exampleObject = Old(
      field1 = "Field1",
      field2 = 1,
      field3 = "Field3"
    )

    val exportedContent = write[Old](exampleObject).getBytes()
    val path = writeFile(dir, fileName, exportedContent)

    /****New field****/
    //Upgrade
    val newWithAddedField = dirFile.readFile[Old](fileName).toNewWithAddedField
    val newWithAddedFieldFileName = "newWithAddedField.json"
    val newWithAddedFieldContent = write[NewWithAddedField](newWithAddedField).getBytes()
    val newWithAddedFieldPath = writeFile(dir, newWithAddedFieldFileName, newWithAddedFieldContent)
    //Downgrade
    Try(dirFile.readFile[Old](newWithAddedFieldFileName)) match {
      case Success(value) => println(s"Downgrade ok with newWithAddedFieldFileName: ${value}")
      case Failure(exception) => println(s"Downgrade error with newWithAddedFieldFileName: ${exception}")
    }

    println("-----------------------------------------------------------------")

    /****New optional field****/
    //Upgrade
    val newWithAddedOptionalField = dirFile.readFile[Old](fileName).toNewWithAddedOptionalField
    val newWithAddedOptionalFieldFileName = "newWithAddedOptionalField.json"
    val newWithAddedOptionalFieldContent = write[NewWithAddedOptionalField](newWithAddedOptionalField).getBytes()
    val newWithAddedOptionalFieldPath = writeFile(dir, newWithAddedOptionalFieldFileName, newWithAddedOptionalFieldContent)
    //Downgrade
    Try(dirFile.readFile[Old](newWithAddedOptionalFieldFileName)) match {
      case Success(value) => println(s"Downgrade ok with newWithAddedOptionalField: ${value}")
      case Failure(exception) => println(s"Downgrade error with newWithAddedOptionalField: ${exception}")
    }

    println("-----------------------------------------------------------------")

    /****New field with default****/
    //Upgrade
    val newWithAddedFieldWithDefault = dirFile.readFile[Old](fileName).toNewWithAddedFieldWithDefault
    val newWithAddedFieldWithDefaultFileName = "newWithAddedFieldWithDefault.json"
    val newWithAddedFieldWithDefaultContent = write[NewWithAddedFieldWithDefault](newWithAddedFieldWithDefault).getBytes()
    val newWithAddedFieldWithDefaultPath = writeFile(dir, newWithAddedFieldWithDefaultFileName, newWithAddedFieldWithDefaultContent)
    //Downgrade
    Try(dirFile.readFile[Old](newWithAddedFieldWithDefaultFileName)) match {
      case Success(value) => println(s"Downgrade ok with newWithAddedFieldWithDefault: ${value}")
      case Failure(exception) => println(s"Downgrade error with newWithAddedFieldWithDefault: ${exception}")
    }

    println("-----------------------------------------------------------------")

    /****New with dropped field****/
    //Upgrade
    val newWithDroppedField = dirFile.readFile[Old](fileName).toNewWithDroppedField
    val newWithDroppedFieldFileName = "newWithDroppedField.json"
    val newWithDroppedFieldContent = write[NewWithDroppedField](newWithDroppedField).getBytes()
    val newWithDroppedFieldPath = writeFile(dir, newWithDroppedFieldFileName, newWithDroppedFieldContent)
    //Downgrade
    Try(dirFile.readFile[Old](newWithDroppedFieldFileName)) match {
      case Success(value) => println(s"Downgrade ok with newWithDroppedField: ${value}")
      case Failure(exception) => println(s"Downgrade error with newWithDroppedField: ${exception}")
    }

    println("-----------------------------------------------------------------")

    /****New with changed types****/
    //Upgrade
    val newWithChangedTypes = dirFile.readFile[Old](fileName).toNewWithChangedTypes
    val newWithChangedTypesFileName = "newWithChangedTypes.json"
    val newWithChangedTypesContent = write[NewWithChangedTypes](newWithChangedTypes).getBytes()
    val newWithChangedTypesPath = writeFile(dir, newWithChangedTypesFileName, newWithChangedTypesContent)
    //Downgrade
    Try(dirFile.readFile[Old](newWithChangedTypesFileName)) match {
      case Success(value) => println(s"Downgrade ok with newWithChangedTypes: ${value}")
      case Failure(exception) => println(s"Downgrade error with newWithChangedTypes: ${exception}")
    }

    println("-----------------------------------------------------------------")

    /****New with renamed field****/
    //Upgrade
    val newWithRenamedField = dirFile.readFile[Old](fileName).toNewWithRenamedField
    val newWithRenamedFieldFileName = "newWithRenamedField.json"
    val newWithRenamedFieldContent = write[NewWithRenamedField](newWithRenamedField).getBytes()
    val newWithRenamedFieldPath = writeFile(dir, newWithRenamedFieldFileName, newWithRenamedFieldContent)
    //Downgrade
    Try(dirFile.readFile[Old](newWithRenamedFieldFileName)) match {
      case Success(value) => println(s"Downgrade ok with newWithRenamedField: ${value}")
      case Failure(exception) => println(s"Downgrade error with newWithRenamedField: ${exception}")
    }

  }

  def writeFile(dir: String, fileName: String, exported: Array[Byte]) = {
    val dirPath = createDir(dir)
    val filePath =s"${dirPath.toPath.toAbsolutePath}/${fileName}"
    Files.write(new File(filePath).toPath, exported)
    filePath
  }

  def createDir(pathToCreate: String): File = {
    val folder = new File(pathToCreate)
    if (Files.notExists(folder.toPath)) {
      Files.createDirectories(folder.toPath)
    }
    folder
  }

}
