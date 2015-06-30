package com.zebra.rest.mapper;

import com.zebra.rest.mapper.exception.MapperException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;

public abstract class Mapper<T, E> {


	public abstract T map(E e) throws MapperException;

    public List<T> map(Collection<E> inputList){
        ArrayList<T> outputList = new ArrayList();
        for (E input : inputList) {
            try {
                outputList.add(map(input));
            } catch (MapperException ex) {
                Logger.getLogger(Mapper.class).error(ex);
            }
        }
        return outputList;
    }
}
