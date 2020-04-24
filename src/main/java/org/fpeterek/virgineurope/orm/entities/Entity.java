package org.fpeterek.virgineurope.orm.entities;

import org.fpeterek.virgineurope.orm.sql.Delete;
import org.fpeterek.virgineurope.orm.sql.Insert;
import org.fpeterek.virgineurope.orm.sql.Update;

public abstract class Entity {

  public void add(Entity entity) { }

  public abstract void formDelete(Delete query);
  public abstract void formUpdate(Update query);
  public abstract void formInsert(Insert query);

}
