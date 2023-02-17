package com.lab.rabbitmq.consumer;

public final class TopicNames {
	public static final String EXCHANGE_MSG = "exch-orders";
	public static final String EXCHANGE_MSG_DLQ = "exch-orders-dlq";
	public static final String EXCHANGE_MSG_MANUAL = "exch-orders-manual";
	public static final String MESSAGE_KEY_ORDERS = "msg.order.#";
	public static final String MESSAGE_KEY_ORDERS_DLQ = "dlq.order.#";
	public static final String MESSAGE_KEY_ORDERS_MANUAL = "manual.order.#";
	public static final String QUEUE_NAME_PROCESS = "exch-orders.process";
	public static final String QUEUE_NAME_DLQ = "exch-orders.dlq";
	public static final String QUEUE_NAME_MANUAL = "exch-orders.manual";
}
