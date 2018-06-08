package pl.asc.claimsservice.shared.specification;

import pl.asc.claimsservice.shared.exceptions.BusinessException;

public class SpecificationNotSatisfiedException extends BusinessException {
    public SpecificationNotSatisfiedException(String errorCode,Object[] params){
        super(errorCode, params);
    }
}
