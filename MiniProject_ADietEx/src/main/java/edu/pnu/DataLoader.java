package edu.pnu;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.FoodList;
import edu.pnu.domain.Member;
import edu.pnu.domain.enums.Role;
import edu.pnu.persistence.FoodListRepository;
import edu.pnu.persistence.MemberRepository;

@Component
public class DataLoader implements CommandLineRunner{

    private final MemberRepository memRepo;
    private final FoodListRepository foodRepo;
    private final Resource jsonData;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DataLoader(FoodListRepository foodRepo, MemberRepository memRepo, @Value("classpath:food_list.json") Resource jsonData){
        this.foodRepo = foodRepo;
        this.memRepo = memRepo;
        this.jsonData = jsonData;
    }

    @Override
    public void run(String... args) throws Exception {
        // add member
        Member testMember1 = Member
        .builder()
        .username("test1")
        .password(passwordEncoder.encode("1234"))
        .role(Role.ROLE_MEMBER)
        .build();

        Member testMember2 = Member
        .builder()
        .username("test2")
        .password(passwordEncoder.encode("1234"))
        .role(Role.ROLE_MEMBER)
        .build();
        
        memRepo.save(testMember1);
        memRepo.save(testMember2);

        // add food list
        ObjectMapper objectMapper = new ObjectMapper();
        List<FoodList> foodList = Arrays.asList(objectMapper.readValue(jsonData.getInputStream(), FoodList[].class));
        
        foodRepo.saveAll(foodList);
    }

}
