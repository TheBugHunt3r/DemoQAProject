package tests.kafka;

import core.kafka.KafkaClientConsumer;
import core.kafka.KafkaClientProducer;
import io.qameta.allure.*;
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
        producer.sendMessage(TOPIC_NAME, "user_123", "{\"status\": \"SUCCESS\", \"test\": \"LoginTest\"}");
        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    ConsumerRecords<String, String> records = consumer.pollMessages(Duration.ofMillis(500));
                    Assert.assertFalse(records.isEmpty(), "Ожидали сообщение в Kafka, но топик пуст!");

                    String lastMessage = records.iterator().next().value();
                    Assert.assertTrue(lastMessage.contains("SUCCESS"));
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