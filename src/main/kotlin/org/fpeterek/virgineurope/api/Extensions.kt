package org.fpeterek.virgineurope.api

import org.fpeterek.virgineurope.orm.Attribute
import org.fpeterek.virgineurope.orm.BooleanExpr
import org.fpeterek.virgineurope.orm.sql.Select
import org.fpeterek.virgineurope.orm.tables.Table

object SELECT {
    infix fun FROM(table: Table) = Select.from(table)
}

infix fun Attribute.EQ(other: Attribute): BooleanExpr = eq(other)

infix fun Attribute.LIKE(other: String): BooleanExpr = like(other)
infix fun Attribute.ILIKE(other: String): BooleanExpr = ilike(other)

infix fun Attribute.EQ(other: Any): BooleanExpr = eq(other)
infix fun Attribute.NEQ(other: Any): BooleanExpr = neq(other)
infix fun Attribute.LT(other: Any): BooleanExpr = lt(other)
infix fun Attribute.LTE(other: Any): BooleanExpr = lte(other)
infix fun Attribute.GT(other: Any): BooleanExpr = lt(other)
infix fun Attribute.GTE(other: Any): BooleanExpr = lte(other)

infix fun BooleanExpr.AND(other: BooleanExpr): BooleanExpr = and(other)
infix fun BooleanExpr.OR(other: BooleanExpr): BooleanExpr = or(other)

infix fun Select.JOIN(table: Table) = join(table)
infix fun Select.LEFT_JOIN(table: Table) = leftJoin(table)
infix fun Select.RIGHT_JOIN(table: Table) = rightJoin(table)
infix fun Select.CARTESIAN_JOIN(table: Table) = cartesianJoin(table)

infix fun Select.WHERE(cond: BooleanExpr) = where(cond)

infix fun Select.JoinObject.ON(cond: BooleanExpr) = on(cond)
