package cn.hba.syslog.consumer;import cn.hba.service.HBaseService;import lombok.extern.slf4j.Slf4j;import org.apache.hadoop.hbase.Cell;import org.apache.hadoop.hbase.util.Bytes;import org.apache.kafka.clients.consumer.ConsumerRecord;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.kafka.annotation.KafkaListener;import org.springframework.stereotype.Component;import java.util.HashMap;import java.util.List;import java.util.Map;/** * syslog 消费者 * * @author wbw * @date 2019/11/6 10:50 */@Component@Slf4jpublic class SyslogConsumer {    @Autowired    HBaseService ihBaseService;//    public void updateOrInsert(CheckInfo checkInfo) {//        Map<String, String> columnValues = new HashMap<>(8);//        Long productId = checkInfo.getProductId();//        Integer deltaId = checkInfo.getDeltaId();//        String mid = checkInfo.getMid();//        String rowKeyRegex = rowKeyRegex(productId, deltaId, mid);//        List<Cell> result = ihBaseService.scanRegexRowKey(tableName, rowKeyRegex);//        columnValues.put("check_time", checkInfo.getCreateTime());//        String rowKey;//        if (result != null) {//            //读出key,插入数据//            Cell cell = result.get(0);//            rowKey = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());//            log.info("scan the key from hbase is :" + rowKey);//        } else {//            //插入新记录//            rowKey = checkInfo.getProductId() + "+" + remainingTime() + "+" + checkInfo.getDeltaId() + "+" + checkInfo.getMid();//            log.info(" the new rowKey is :" + rowKey);//            columnValues.put("status", BaseConfig.STATUS_CHECK_SUCCESS);//        }//        ihBaseService.putRowValueBatch(tableName, rowKey, familyColumn, columnValues);//    }    @KafkaListener(topics = "attack")    public void attack(ConsumerRecord<?, ?> record) {        log.info("attack topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }    @KafkaListener(topics = "flow")    public void flow(ConsumerRecord<?, ?> record) {        log.info("flow topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }    @KafkaListener(topics = "menace")    public void menace(ConsumerRecord<?, ?> record) {        log.info("menace topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }    @KafkaListener(topics = "strategy")    public void strategy(ConsumerRecord<?, ?> record) {        log.info("strategy topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }    @KafkaListener(topics = "sysrun")    public void sysrun(ConsumerRecord<?, ?> record) {        log.info("sysrun topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }    @KafkaListener(topics = "opconf")    public void opconf(ConsumerRecord<?, ?> record) {        log.info("opconf topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }    @KafkaListener(topics = "security")    public void security(ConsumerRecord<?, ?> record) {        log.info("security topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }    @KafkaListener(topics = "hardware")    public void hardware(ConsumerRecord<?, ?> record) {        log.info("hardware topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }    @KafkaListener(topics = "network")    public void network(ConsumerRecord<?, ?> record) {        log.info("network topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }    @KafkaListener(topics = "operation")    public void operation(ConsumerRecord<?, ?> record) {        log.info("operation topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }    @KafkaListener(topics = "other")    public void other(ConsumerRecord<?, ?> record) {        log.info("other topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());    }}