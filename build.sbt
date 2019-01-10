val scalaV = "2.12.8"
val scalaJsV = "0.6.26"

lazy val root = project.in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    organization  := "com.github.lettenj61",
    scalaVersion  := scalaV,
    version       := "0.1.0-SNAPSHOT",
    name          := "Metaphor",
    libraryDependencies ++= Seq(
      "org.scala-js"  %%% "scalajs-tools" % scalaJsV,
      "org.scalameta" %%% "scalameta"     % "4.1.0",
      "com.lihaoyi"   %%% "pprint"        % "0.5.3"
    ),
    scalaJSLinkerConfig ~= { cf =>
      cf.withSourceMap(false)
        .withModuleKind(ModuleKind.CommonJSModule)
    },
    scalaJSUseMainModuleInitializer := true,
    mainClass in Compile := Some("metaphor.Cli")
  )
