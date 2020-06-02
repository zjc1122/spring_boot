import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.IAnimal;

import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ApplicationTests {

    @Test
    public void test() {

        ServiceLoader<IAnimal> iAnimals = ServiceLoader.load(IAnimal.class);

        iAnimals.forEach(IAnimal::say);

        AtomicInteger integer = new AtomicInteger();
    }

}
