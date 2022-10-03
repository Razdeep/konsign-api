package com.razdeep.konsignapi;

import com.razdeep.konsignapi.entity.CollectionVoucherEntity;
import com.razdeep.konsignapi.entity.CollectionVoucherItemEntity;
import com.razdeep.konsignapi.repository.BillEntryRepository;
import com.razdeep.konsignapi.repository.BuyerRepository;
import com.razdeep.konsignapi.repository.CollectionVoucherRepository;
import com.razdeep.konsignapi.service.BillEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})
public class KonsignApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KonsignApiApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private BillEntryRepository billEntryRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private CollectionVoucherRepository collectionVoucherRepository;

    @Override
    public void run(String... args) {

        CollectionVoucherItemEntity collectionVoucherItemEntity = CollectionVoucherItemEntity.builder()
                .amountCollected(100.f)
                .bank("hdfc")
                .ddNo("123")
                .ddDate("2 Jan")
                .bill(billEntryRepository.findById("fdg").isPresent() ? billEntryRepository.findById("fdg").get() : null)
                .build();

        List<CollectionVoucherItemEntity> llist = new ArrayList<>();

        CollectionVoucherEntity collectionVoucher = CollectionVoucherEntity.builder()
                .voucherNo("001")
                .voucherDate("1st Jan")
                .buyer(buyerRepository.findBuyerByBuyerName("babu"))
                .build();

        collectionVoucherItemEntity.setCollectionVoucher(collectionVoucher);
        collectionVoucher.setCollectionVoucherItemEntityList(llist);

        llist.add(collectionVoucherItemEntity);

        collectionVoucherRepository.save(collectionVoucher);
    }
}
