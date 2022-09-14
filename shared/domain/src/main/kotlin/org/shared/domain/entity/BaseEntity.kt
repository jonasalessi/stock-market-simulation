package org.shared.domain.entity


abstract class AggregateRoot<ID>: Entity<ID>()

abstract class Entity<ID> {
    abstract val id: ID
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entity<*>

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode() ?: 0
    }

}