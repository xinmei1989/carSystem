package com.jkxy.car.api.service.Impl;

import com.jkxy.car.api.dao.CarDao;
import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.pojo.PageInfo;
import com.jkxy.car.api.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("carService")
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    @Override
    public List<Car> findAll() {
        return carDao.findAll();
    }

    @Override
    public Car findById(int id) {
        return carDao.findById(id);
    }

    @Override
    public List<Car> findByCarName(String carName) {
        return carDao.findByCarName(carName);
    }

    @Override
    public void deleteById(int id) {
        carDao.deleteById(id);
    }

    @Override
    public void updateById(Car car) {
        carDao.updateById(car);
    }

    @Override
    public void insertCar(Car car) {
        carDao.insertCar(car);
    }

    @Override
    @Transactional
    public synchronized int purchaseCar(int id) {
        Car car = carDao.findById(id);
        if (car == null) {
            return 1; // 没有库存
        }
        this.deleteById(id);
        return 0;
    }

    @Override
    @Transactional
    public List<Car> queryByPage(PageInfo pageInfo) {
        String carName = "%" + pageInfo.getBrand() + "%";
        List<Car> cars = carDao.queryByCarName(carName);
        int start = pageInfo.getPageSize() * (pageInfo.getPageNum() - 1) + 1;
        int end = start + pageInfo.getPageSize();
        return cars.subList(start-1, end-1);
    }
}
