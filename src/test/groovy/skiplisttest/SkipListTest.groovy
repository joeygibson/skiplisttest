package skiplisttest

import spock.lang.Specification

class SkipListTest extends Specification {
    def impl = new SkipList()

    def setup() {
        impl.put("ABC", 123)
        impl.put("DEF", 123)
        impl.put("KLM", 123)
        impl.put("HIJ", 123)
        impl.put("GHJ", 123)
        impl.put("AAA", 123)
    }

    def firstTest() {
        when:
        println("foo")

        then:
        impl != null
        impl.printHorizontal()
    }
}
