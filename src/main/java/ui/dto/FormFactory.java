package ui.dto;

import com.github.javafaker.Faker;

public class FormFactory {

    private static final Faker faker = new Faker();

    // Убрали неиспользуемые параметры gender и stateAndCity
    public static FormData getForm() {
        return baseBuilder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .mobile(faker.number().digits(10))
                .subjects(faker.funnyName().name())
                .currentAddress(faker.address().fullAddress())
                .build();
    }

    public static FormData getTextBox() {
        return baseBuilder()
                .email(faker.internet().emailAddress())
                .currentAddress(faker.address().fullAddress())
                .permanentAddress(faker.address().fullAddress())
                .build();
    }

    public static FormData getWebPages() {
        return baseBuilder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .age(faker.number().digits(3))
                .salary(faker.number().digits(5))
                .department(faker.company().name())
                .build();
    }

    // Общий метод для всех билдеров
    private static FormData.FormDataBuilder baseBuilder() {
        return FormData.builder();
    }
}
