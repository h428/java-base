package com.hao.base.common.perm.processor;

import java.util.Set;

public interface ThreadPermApi {

    Set<String> queryPerms();

    Set<String> queryRoles();

}
