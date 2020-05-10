package org.fpeterek.virgineurope.api

import org.fpeterek.virgineurope.orm.Attribute
import org.fpeterek.virgineurope.orm.BooleanExpr
import org.fpeterek.virgineurope.orm.sql.Select
import org.fpeterek.virgineurope.orm.tables.Table

object SELECT {
    infix fun FROM(table: Table): Select = Select.from(table)
}

infix fun Attribute.EQ(other: Attribute): BooleanExpr = eq(other)

infix fun Attribute.LIKE(other: String): BooleanExpr = like(other)
infix fun Attribute.ILIKE(other: String): BooleanExpr = ilike(other)

infix fun Attribute.EQ(other: Any): BooleanExpr = eq(other)
infix fun Attribute.NEQ(other: Any): BooleanExpr = neq(other)
infix fun Attribute.LT(other: Any): BooleanExpr = lt(other)
infix fun Attribute.LTE(other: Any): BooleanExpr = lte(other)
infix fun Attribute.GT(other: Any): BooleanExpr = gt(other)
infix fun Attribute.GTE(other: Any): BooleanExpr = gte(other)

infix fun BooleanExpr.AND(other: BooleanExpr): BooleanExpr = and(other)
infix fun BooleanExpr.OR(other: BooleanExpr): BooleanExpr = or(other)

infix fun Select.JOIN(table: Table): Select.JoinObject = join(table)
infix fun Select.LEFT_JOIN(table: Table): Select.JoinObject = leftJoin(table)
infix fun Select.RIGHT_JOIN(table: Table): Select.JoinObject = rightJoin(table)
infix fun Select.CARTESIAN_JOIN(table: Table): Select.JoinObject = cartesianJoin(table)

infix fun Select.WHERE(cond: BooleanExpr): Select = where(cond)

infix fun Select.JoinObject.ON(cond: BooleanExpr): Select = on(cond)
