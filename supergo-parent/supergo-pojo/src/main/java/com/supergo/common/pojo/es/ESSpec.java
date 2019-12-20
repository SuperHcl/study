package com.supergo.common.pojo.es;

import com.supergo.common.pojo.Specification;
import com.supergo.common.pojo.SpecificationOption;

import java.util.List;

public class ESSpec {

    private String specShortName;
    private Specification specification;
    private List<SpecificationOption> specificationOptionList;

    public String getSpecShortName() {
        return specShortName;
    }

    public void setSpecShortName(String specShortName) {
        this.specShortName = specShortName;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }
}
