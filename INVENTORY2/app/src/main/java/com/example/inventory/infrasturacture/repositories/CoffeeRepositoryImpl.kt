package com.example.inventory.infrasturacture.repositories

import com.example.inventory.domain.entities.CoffeeItem
import com.example.inventory.infrasturacture.datasources.local.CoffeeLocalDataSource
import com.example.inventory.infrasturacture.dtos.CoffeeItemDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CoffeeRepositoryImpl(
    private val localDataSource: CoffeeLocalDataSource
) : CoffeeRepository {

    override fun getCoffeeItems(): Flow<Result<List<CoffeeItem>>> = flow {
        try {
            val coffeeItems = localDataSource.getCoffeeItems().map { it.toDomain() }
            emit(Result.success(coffeeItems))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun searchCoffeeItems(query: String): Flow<Result<List<CoffeeItem>>> = flow {
        try {
            val coffeeItems = localDataSource.searchCoffeeItems(query).map { it.toDomain() }
            emit(Result.success(coffeeItems))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun addCoffeeItem(coffeeItem: CoffeeItem): Result<Unit> = try {
        val dto = CoffeeItemDto(
            id = coffeeItem.id,
            name = coffeeItem.name,
            price = coffeeItem.price,
            stock = coffeeItem.stock
        )
        localDataSource.saveCoffeeItem(dto)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateCoffeeItem(coffeeItem: CoffeeItem): Result<Unit> = try {
        val dto = CoffeeItemDto(
            id = coffeeItem.id,
            name = coffeeItem.name,
            price = coffeeItem.price,
            stock = coffeeItem.stock
        )
        localDataSource.updateCoffeeItem(dto)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteCoffeeItem(id: String): Result<Unit> = try {
        localDataSource.deleteCoffeeItem(id)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
