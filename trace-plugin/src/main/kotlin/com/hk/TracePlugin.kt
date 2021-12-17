package com.hk

import com.android.build.gradle.AppExtension
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class TracePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val android = project.extensions.getByType(AppExtension::class.java)
        android.applicationVariants.all { variant ->
            val javaCompile = variant.javaCompileProvider
            javaCompile.get().doLast {
                val args = arrayOf(
                    "-showWeaveInfo",
                    "-1.8",
                    "-inpath",
                    javaCompile.get().destinationDir.toString(),
                    "-aspectpath",
                    javaCompile.get().classpath.asPath,
                    "-d",
                    javaCompile.get().destinationDir.toString(),
                    "-classpath",
                    javaCompile.get().classpath.asPath,
                    "-bootclasspath",
                    android.bootClasspath.joinToString(File.pathSeparator) // TODO: can work ?
                )
                val handler = MessageHandler(true)
                Main().run(args, handler)

                handler.getMessages(null, true).forEach { message ->
                    when (message.kind) {
                        IMessage.ABORT,
                        IMessage.ERROR,
                        IMessage.FAIL -> {
                            """
                                TracePlugin #apply()
                                ERROR: ${message.message}, ${message.thrown}
                            """.trimIndent()
                        }

                        IMessage.WARNING,
                        IMessage.INFO -> {
                            """
                                TracePlugin #apply()
                                INFO: ${message.message}, ${message.thrown}
                            """.trimIndent()
                        }

                        IMessage.DEBUG -> {
                            """
                                TracePlugin #apply()
                                DEBUG: ${message.message}, ${message.thrown}
                            """.trimIndent()
                        }

                        else -> {
                            // No need to handle
                        }
                    }
                }
            }
        }
    }
}