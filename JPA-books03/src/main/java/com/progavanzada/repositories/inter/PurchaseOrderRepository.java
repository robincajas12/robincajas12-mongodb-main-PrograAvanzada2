package com.progavanzada.repositories.inter;

import com.progavanzada.models.PurchaseOrder;
import com.progavanzada.repositories.ClassEntity;

@ClassEntity(PurchaseOrder.class)
public interface PurchaseOrderRepository extends CRUD<PurchaseOrder, Long> {
}
