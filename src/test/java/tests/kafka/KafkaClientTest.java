package tests.kafka;

import core.kafka.KafkaClientConsumer;
import core.kafka.KafkaClientProducer;
import io.qameta.allure.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.awaitility.Awaitility;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Epic("Интеграции")
@Feature("Kafka Messaging Service")
public class KafkaClientTest {

    private static final String TOPIC_NAME = "test-execution-results";

    private KafkaClientProducer producer;
    private KafkaClientConsumer consumer;

    @BeforeMethod
    public void setUpKafka() {
        producer = new KafkaClientProducer();
        consumer = new KafkaClientConsumer(TOPIC_NAME);
    }

    @Test(description = "Проверка отправки и вычитки сообщения из Kafka")
    @Story("Отправка и чтение сообщений из топика")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Интеграционный тест: отправка текстового JSON-сообщения через Producer в топик Kafka и его " +
            "асинхронное получение через Consumer (Awaitility).")
    public void testKafkaMessageFlow() {
        String expectedMessage = "{\"status\": \"SUCCESS\", \"test\": \"LoginTest\"}";
        producer.sendMessage(TOPIC_NAME, "user_123", expectedMessage);
        System.out.println(">>> Сообщение отправлено ДО ожидания консьюмера");
        Awaitility.await()
                .atMost(45, TimeUnit.SECONDS)
                .pollInterval(Duration.ofMillis(100))
                .until(consumer::hasAssignedPartitions);
        Awaitility.await()
                .atMost(30, TimeUnit.SECONDS)
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                    ConsumerRecords<String, String> records = consumer.pollMessages(Duration.ofSeconds(2));
                    boolean found = false;
                    System.out.println(">>> Polled records count: " + records.count());
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println(">>> Прочитано из Kafka -> Значение: " + record.value());
                        if (record.value() != null && record.value().contains("SUCCESS")) {
                            found = true;
                            break;
                        }
                    }
                    Assert.assertTrue(found, "Ожидали сообщение со статусом SUCCESS в Kafka, но топик пока пуст или не содержит его!");
                });
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownKafka() {
        if (producer != null) {
            producer.close();
        }
        if (consumer != null) {
            consumer.close();
        }
    }
}