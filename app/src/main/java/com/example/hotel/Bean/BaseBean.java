package com.example.hotel.Bean;

public class BaseBean  {
        private Data data;
        private int status;
        private String desc;
        public void setData(Data data) {
            this.data = data;
        }
        public Data getData() {
            return data;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
        public String getDesc() {
            return desc;
        }

    @Override
    public String toString() {
        return "BaseBean{" +
                "data=" + data +
                ", status=" + status +
                ", desc='" + desc + '\'' +
                '}';
    }
}
