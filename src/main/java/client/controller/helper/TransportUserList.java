package client.controller.helper;

import client.model.TransportUser;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;

@JsonRootName("TransportUser")
public interface TransportUserList extends List<TransportUser> {
}
