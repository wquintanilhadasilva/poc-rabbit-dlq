FROM rabbitmq:3.7-management

ADD ./etc/rabbitmq.config /etc/rabbitmq/
ADD ./etc/definitions.json /etc/rabbitmq/

RUN chown rabbitmq:rabbitmq /etc/rabbitmq/definitions.json
RUN chown rabbitmq:rabbitmq /etc/rabbitmq/rabbitmq.config
