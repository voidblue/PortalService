public enum DaoFatory {
    INSTANCE;

    public UserDao getUserDao(){
        return new UserDao(getConnectionMaker());
    }


    public ConnectionMaker getConnectionMaker(){
        return new JejuConnectionMaker();
    }
}
