package com.ramonli.sandbox.mybatis;

import java.io.InputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisMain {
	private static Logger logger = LoggerFactory.getLogger(MybatisMain.class);
	private SqlSessionFactory sessionFactory;

	public MybatisMain() throws Exception {
		String resource = "com/ramonli/sandbox/mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	public Blog selectOneBlog(long id) throws Exception {
		SqlSession session = sessionFactory.openSession();
		try {
			// Blog blog =
			// session.selectOne("com.ramonli.sandbox.mybatis.BlogMapper.selectBlog",
			// 101);
			BlogMapper mapper = session.getMapper(BlogMapper.class);
			Blog blog = mapper.selectById(101l);
			return blog;
		} finally {
			session.close();
		}
	}

	public static void main(String[] args) throws Exception {
		MybatisMain main = new MybatisMain();
		Blog blog = main.selectOneBlog(101l);
		logger.debug(ToStringBuilder.reflectionToString(blog));
	}

}
