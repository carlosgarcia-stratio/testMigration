package com.stratio.qa.models

case class Old(field1: String, field2: Int, field3: String)  {
  def toNewWithAddedField: NewWithAddedField =
    NewWithAddedField(
      field1 = field1,
      field2 = field2,
      field3 = field3,
      field4 = "Default"
    )

  def toNewWithAddedOptionalField: NewWithAddedOptionalField =
    NewWithAddedOptionalField(
      field1 = field1,
      field2 = field2,
      field3 = field3,
      field4 = Some("Default")
    )

  def toNewWithAddedFieldWithDefault: NewWithAddedFieldWithDefault =
    NewWithAddedFieldWithDefault(
      field1 = field1,
      field2 = field2,
      field3 = field3
    )

  def toNewWithDroppedField: NewWithDroppedField =
    NewWithDroppedField(
      field1 = field1,
      field2 = field2
    )

  def toNewWithChangedTypes: NewWithChangedTypes =
    NewWithChangedTypes(
      field1 = field1,
      field2 = field2.toString,
      field3 = field3
    )

  def toNewWithRenamedField: NewWithRenamedField =
    NewWithRenamedField(
      field1 = field1,
      field2 = field2,
      field3Renamed = field3
    )

}

case class NewWithAddedField(field1: String, field2: Int, field3: String, field4: String)

case class NewWithAddedOptionalField(field1: String, field2: Int, field3: String, field4: Option[String])

case class NewWithAddedFieldWithDefault(field1: String, field2: Int, field3: String, field4: String = "Default")

case class NewWithDroppedField(field1: String, field2: Int)

case class NewWithChangedTypes(field1: String, field2: String, field3: String)

case class NewWithRenamedField(field1: String, field2: Int, field3Renamed: String)
