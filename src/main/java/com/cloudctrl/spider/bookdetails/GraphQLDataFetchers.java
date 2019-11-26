package com.cloudctrl.spider.bookdetails;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cloudctrl.spider.MethodsRepo;
import com.cloudctrl.spider.SpiderClassesRepo;
import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

    @Autowired
    MethodsRepo methodRepo;

    @Autowired
    private SpiderClassesRepo classRepo;

    public DataFetcher getMethodByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            return methodRepo.findById(Long.parseLong(id));
        };
    }

    public DataFetcher getMethods() {
        return dataFetchingEnvironment -> {
            String selector = dataFetchingEnvironment.getArgument("selector");
            int limit = dataFetchingEnvironment.getArgument("limit");
            int offset = dataFetchingEnvironment.getArgument("offset");
            return methodRepo.findAllLike(selector, limit, offset);
        };
    }

    public DataFetcher getClassByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            return classRepo.findById(Long.parseLong(id));
        };
    }

    public DataFetcher getClasses() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            int limit = dataFetchingEnvironment.getArgument("limit");
            int offset = dataFetchingEnvironment.getArgument("offset");
            return classRepo.findAllLike(name, limit, offset);
        };
    }

    public DataFetcher getClassMethods() {
        return env -> {
            String name = env.getArgument("name");
            int limit = env.getArgument("limit");
            int offset = env.getArgument("offset");
            return classRepo.findAllLike(name, limit, offset);
        };
    }

}