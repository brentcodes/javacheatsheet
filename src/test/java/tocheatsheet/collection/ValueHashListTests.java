package tocheatsheet.collection;

import org.junit.Assert;
import org.junit.Test;;


public class ValueHashListTests {

    @Test
    public void testToString() {
        ValueHashList<Integer> subject = new ValueHashList<>();
        subject.add(3);
        subject.add(5);
        subject.add(5);
        Assert.assertEquals("(3,5,5)", subject.toString());
    }

}
