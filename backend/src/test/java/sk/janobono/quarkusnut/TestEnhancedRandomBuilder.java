package sk.janobono.quarkusnut;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldPredicates;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.randomizers.EmailRandomizer;
import io.github.benas.randombeans.randomizers.FirstNameRandomizer;
import sk.janobono.quarkusnut.so.UserSO;

public class TestEnhancedRandomBuilder {

    public static EnhancedRandom build() {
        return EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .randomize(FieldPredicates.named("username").and(FieldPredicates.inClass(UserSO.class)), new FirstNameRandomizer())
                .randomize(FieldPredicates.named("email").and(FieldPredicates.inClass(UserSO.class)), new EmailRandomizer())
                .build();
    }
}
