package org.fpeterek.virgineurope.orm;

import org.fpeterek.virgineurope.orm.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Table {

  public final String tableName;
  protected List<Attribute> attributeArray = new ArrayList<>();
  protected String nameAlias = null;

  protected Table(String table) {
    tableName = table;
  }

  public abstract int offset();
  public abstract Entity parseFrom(ResultSet rs, int offset) throws SQLException;

  protected abstract Table getAliasedTable(String alias);

  public Table as(String alias) {
    Table t = getAliasedTable(alias);
    t.attributeArray.forEach( attribute -> attribute.table = alias );
    return t;
  }

  public boolean hasAlias() {
    return nameAlias != null;
  }

  protected Attribute createAttribute(String attrName, String tableName) {
    var attr = new Attribute(attrName, tableName);
    attributeArray.add(attr);
    return attr;
  }

  public String alias() {
    return nameAlias;
  }
  public List<Attribute> attributes() {
    return attributeArray;
  }

}
