package io.github.untoastedtoast.foldio.persistence

import java.util.concurrent.Callable

interface TransactionalExecutor {
    fun <T> runTransactionally(callable: Callable<T>): T

    fun runTransactionally(runnable: Runnable)
}
