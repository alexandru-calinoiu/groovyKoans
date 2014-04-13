package groovykoans

import grails.transaction.Transactional

@Transactional
class KoanService {

    def runKoan(String koan) {
//        List checks = []
        Binding binding = new Binding()
        def out = new ByteArrayOutputStream()
        binding.setProperty("out", new PrintStream(out))
//        binding.setVariable("check", { boolean result, String message ->
//            checks << [result: result, message: message]
//        })
        GroovyShell shell = new GroovyShell(binding)
        try {
            shell.evaluate(koan)
            [success: true, output: out.toString()]
        } catch (AssertionError e) {
            [success: false, message: e.message, output: out.toString()]
        } catch (Exception e) {
            [success: false, exception: e, message: e.message, output: out.toString()]
        }
//        checks
    }

}