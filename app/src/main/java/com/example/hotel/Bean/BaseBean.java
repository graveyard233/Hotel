package com.example.hotel.Bean;

public class BaseBean <T> {
        private T data;
        private int status;
        private String desc;
        public void setData(T data) {
            this.data = data;
        }
        public T getData() {
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
                "data=" + data.toString() +
                ", status=" + status +
                ", desc='" + desc + '\'' +
                '}';
    }
}
