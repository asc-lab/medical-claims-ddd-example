package pl.asc.claimsservice.domainmodel;

public enum ClaimStatus {
    IN_EVALUATION {
        @Override
        public boolean isEditable() {
            return true;
        }
    },
    EVALUATED {
        @Override
        public boolean isEditable() {
            return false;
        }
    },
    ACCEPTED {
        @Override
        public boolean isEditable() {
            return false;
        }
    },
    REJECTED {
        @Override
        public boolean isEditable() {
            return false;
        }
    };

    abstract public boolean isEditable();
}
