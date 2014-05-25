package groovykoans

import grails.test.mixin.TestFor
import groovyKoans.Koan
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(KoanService)
class KoanServiceSpec extends Specification {
    void "runKoan - assertion is true"() {
        given:
        def koan = /assert true, 'blablabla'/

        when:
        def result = service.runKoan(koan)

        then:
        result.success
    }

    void "runKoan - assertion is false"() {
        given:
        def koan = /assert false, 'blablabla'/

        when:
        def result = service.runKoan(koan)

        then:
        !result.success
        result.message.startsWith 'blablabla'  //TODO: de onzin hier achter weghalen
    }

    void "runKoan - exception"() {
        def koan = /throw new RuntimeException('oh nee. alles gaat mis!')/

        when:
        def result = service.runKoan(koan)

        then:
        result.exception.message == 'oh nee. alles gaat mis!'
    }

    void "runKoan - println"() {
        def koan = /println 'lalala'/

        when:
        def result = service.runKoan(koan)

        then:
        result.success
        result.output == 'lalala\n'
    }

    void "runKoan - System.exit(-1)"() {
        def koan = /System.exit(-1)/

        when:
        def result = service.runKoan(koan)

        then:
        !result.success
        result.exception
    }
}
