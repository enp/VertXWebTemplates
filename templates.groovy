#!/usr/bin/env groovy

@Grab('io.vertx:vertx-core:[3,)')
@Grab('io.vertx:vertx-lang-groovy:[3,)')
@Grab('io.vertx:vertx-web:[3,)')
@Grab('io.vertx:vertx-web-templ-handlebars:[3,)')
@Grab('io.vertx:vertx-web-templ-freemarker:[3,)')
@Grab('io.vertx:vertx-web-templ-pebble:[3,)')

def vertx  = io.vertx.groovy.core.Vertx.vertx()
def router = io.vertx.groovy.ext.web.Router.router(vertx)

def engines = [
  'hbs' : io.vertx.groovy.ext.web.templ.HandlebarsTemplateEngine.create(),
  'ftl' : io.vertx.groovy.ext.web.templ.FreeMarkerTemplateEngine.create(),
  'peb' : io.vertx.groovy.ext.web.templ.PebbleTemplateEngine.create(vertx)
]

router.get().handler({ context ->
  def engine = context.request().getParam("engine")
  context.put("items", ["one","two","tree"])
  engines[engine].render(context, "templates/view.${engine}", { result ->
    if (result.succeeded()) {
      context.response().end(result.result())
    } else {
      context.fail(result.cause())
    }
  })
})

vertx.createHttpServer().requestHandler(router.&accept).listen(8100)
