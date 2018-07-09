package pl.asc.claimsservice.domainmodel;

public enum ClaimStatus {
    IN_EVALUATION {
        @Override
        boolean allowsEdition() {
            return true;
        }

        @Override
        boolean allowsRejection() {
            return true;
        }

        @Override
        public boolean allowsAcceptance() {
            return false;
        }
    },
    EVALUATED {
        @Override
        boolean allowsEdition() {
            return false;
        }

        @Override
        boolean allowsRejection() {
            return true;
        }

        @Override
        public boolean allowsAcceptance() {
            return true;
        }
    },
    ACCEPTED {
        @Override
        boolean allowsEdition() {
            return false;
        }

        @Override
        boolean allowsRejection() {
            return false;
        }

        @Override
        public boolean allowsAcceptance() {
            return false;
        }
    },
    REJECTED {
        @Override
        boolean allowsEdition() {
            return false;
        }

        @Override
        boolean allowsRejection() {
            return false;
        }

        @Override
        public boolean allowsAcceptance() {
            return false;
        }
    };

    abstract boolean allowsEdition();

    abstract boolean allowsRejection();

    public abstract boolean allowsAcceptance();
}
