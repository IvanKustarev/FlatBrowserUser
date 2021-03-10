package L6User.ApartmentDescription;

import L6User.ApartmentDescription.Attractive;

public enum View implements Attractive {
    BAD{
        @Override
        public int levelAttractive() {
            return 1;
        }
    },
    NORMAL {
        @Override
        public int levelAttractive() {
            return 2;
        }
    },
    GOOD {
        @Override
        public int levelAttractive() {
            return 3;
        }
    },
    TERRIBLE {
        @Override
        public int levelAttractive() {
            return 0;
        }
    };
}