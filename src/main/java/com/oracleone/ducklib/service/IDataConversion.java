package com.oracleone.ducklib.service;

public interface IDataConversion {
    <T> T obtainData(String json, Class<T> tClass);
}
