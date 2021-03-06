package io.github.tcdl.msb

import com.fasterxml.jackson.module.scala.DefaultScalaModule

import akka.actor.ActorSystem
import akka.actor.ExtendedActorSystem
import akka.actor.Extension
import akka.actor.ExtensionId
import akka.actor.ExtensionIdProvider
import io.github.tcdl.msb.api.MsbContextBuilder
import io.github.tcdl.msb.support.Utils

class MsbImpl(system: ActorSystem) extends Extension {

  // init jackson for marshalling the json body in the payload
  Utils.getJsonObjectMapper.registerModule(DefaultScalaModule)

  val config = MsbConfig(system).msbConfig

  val context = new MsbContextBuilder()
    .withConfig(config)
    .withShutdownHook(true)
    .build()

  system.registerOnTermination { context.shutdown() }

}

object Msb extends ExtensionId[MsbImpl] with ExtensionIdProvider {
  override def createExtension(system: ExtendedActorSystem): MsbImpl = new MsbImpl(system)
  override def lookup = Msb
}