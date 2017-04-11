import com.zalas.masterthesis.apt.pet.framework.Pet;
import com.zalas.masterthesis.apt.pet.framework.PetCase;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

@Pet
public class MeanExecutionTimePET {

    @PetCase
    public void meanExecutionTime_shouldBeOnAcceptableLevel() throws Exception{
        double meanExecutionTimeFromLastOneMinute = 1200.5641;

        assertThat(meanExecutionTimeFromLastOneMinute, lessThan(1200.0));
    }

    public void test1() {

    }

    private void test2() {

    }
}
