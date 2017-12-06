package solonarv.mods.thegreatweb.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import solonarv.mods.thegreatweb.common.leyweb.LeyLine;
import solonarv.mods.thegreatweb.common.leyweb.LeyNode;
import solonarv.mods.thegreatweb.common.leyweb.LeyNodeGroup;
import solonarv.mods.thegreatweb.common.leyweb.LeyWeb;

import java.util.Set;

public class LeyWebDataPacket extends RequestLeyWebDataPacket {
    private LeyNode[] nodes;
    private LeyLine[] edges;

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        int numNodes = buf.readByte();
        nodes = new LeyNode[numNodes];
        for (int i=0; i < numNodes; i++) {
            int id = buf.readInt();
            int x = buf.readInt();
            int z = buf.readInt();
            nodes[i] = new LeyNode(x, z, id);
        }

        int numEdges = buf.readInt();
        edges = new LeyLine[numEdges];
        for (int i=0; i < numEdges; i++) {
            int from = buf.readInt();
            int to = buf.readInt();
            int strength = buf.readInt();
            edges[i] = new LeyLine(from, to, strength);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        buf.writeInt(nodes.length);
        for (LeyNode node : nodes) {
            buf.writeInt(node.id);
            buf.writeInt(node.getX());
            buf.writeInt(node.getZ());
        }

        buf.writeInt(edges.length);
        for (LeyLine edge : edges) {
            buf.writeInt(edge.getSource());
            buf.writeInt(edge.getTarget());
            buf.writeInt(edge.getFlowAmount());
        }
    }

    public LeyWebDataPacket setFromData(LeyWeb web, int groupX, int groupZ) {
        LeyNodeGroup group = web.getOrGenerateNodeGroup(groupX, groupZ, true);
        nodes = new LeyNode[group.size() + 4];

        int i = 0;
        nodes[i++] = web.getNode(web.getNodeGroup(groupX, groupZ - 1).getSouthNode());
        nodes[i++] = web.getNode(web.getNodeGroup(groupX + 1, groupZ).getWestNode());
        nodes[i++] = web.getNode(web.getNodeGroup(groupX, groupZ + 1).getNorthNode());
        nodes[i++] = web.getNode(web.getNodeGroup(groupX + 1, groupZ).getEastNode());
        for (int id : group) {
            nodes[i++] = web.getNode(id);
        }

        Set<LeyLine> leyLines = web.getEdgesTouchingGroup(group);
        edges = new LeyLine[leyLines.size()];
        int j = 0;
        for (LeyLine leyline : leyLines) {
            edges[j++] = leyline;
        }

        return this;
    }

    public static class Handler implements IMessageHandler<LeyWebDataPacket, IMessage> {

        @Override
        public IMessage onMessage(LeyWebDataPacket message, MessageContext ctx) {
            // TODO write packet data to client-side web
            return null;
        }
    }
}
