package pfb.onecode.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 生产者
 * @author libingyang
 * @date 2018年6月4日上午11:50:45
 */
public class Producer {

	public static void main(String[] args) {
		//需要一个producer group名字作为构造方法的参数，这里为producer
		DefaultMQProducer producer = new DefaultMQProducer("Producer");
		//设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
		producer.setNamesrvAddr("10.1.54.121:9876;10.1.54.122:9876");
		 
		 try {
			 producer.start();
			 Message msg = new Message("PushTopic", "push", "1", "Just for push1.".getBytes()); // topic tag  key  body
			 SendResult send = producer.send(msg);
			 System.out.println(send.getMsgId()+send.getSendStatus());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}

	}

}
