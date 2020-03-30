package com.deividasstr.data.store.dbs

import com.deividasstr.data.DbFact
import com.deividasstr.data.FactsDbQueries
import com.deividasstr.data.R
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.entities.models.Fact
import com.deividasstr.domain.monads.Either
import javax.inject.Singleton

@Singleton
class FactsDb(private val queries: FactsDbQueries) : FactsDao {

    override suspend fun addFacts(facts: List<DbFact>): Either<Error, Either.None> {
        queries.transaction {
            facts.forEach {
                queries.insert(it)
            }
        }
        return Either.Right(Either.None())
    }

    override suspend fun getFact(currentFactId: Long): Either<Error, DbFact> {
        var fact: DbFact
        do {
            fact = queries.getRandom().executeAsOneOrNull()
                ?: return Either.Left(Error(R.string.error_no_facts_available))
        } while (fact.id == currentFactId)
        return Either.Right(fact)
    }
}

fun Fact(dbFact: DbFact): Fact = Fact(dbFact.id, dbFact.text)
fun DbFact(fact: Fact): DbFact = DbFact.Impl(fact.id, fact.text)
