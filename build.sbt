lazy val root = project.in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    inThisBuild(List(
      organization := "com.github.lettenj61",
      scalaVersion := "2.12.2",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Metaphor",
    libraryDependencies ++= Seq(
      "org.scalameta" %%% "scalameta" % "1.8.0"
    ),
    scalaJSModuleKind := ModuleKind.CommonJSModule,
    scalaJSUseMainModuleInitializer := true,
    mainClass in Compile := Some("metaphor.Cli")
  )
