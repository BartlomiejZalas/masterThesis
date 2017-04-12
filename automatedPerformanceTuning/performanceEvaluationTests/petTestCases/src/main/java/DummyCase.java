import com.zalas.masterthesis.apt.pet.framework.Pet;
import com.zalas.masterthesis.apt.pet.framework.PetCase;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

@Pet
public class DummyCase {

    @PetCase(monitorIntervalInSec = 2, durationInSec = 10)
    public void dummyTestCase() throws Exception{
        assertThat("dummyTest", 1, greaterThan(2));
    }
}
