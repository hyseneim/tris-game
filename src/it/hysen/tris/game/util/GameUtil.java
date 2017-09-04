package it.hysen.tris.game.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import it.hysen.tris.game.Game;
import it.hysen.tris.tree.TreeNode;
import it.hysen.tris.tree.util.TreeUtil;

/**
 * @author Gabriele Cipolloni
 */
public class GameUtil {
	
	private static final Kryo _kryo;
	private static final ObjectMapper _mapper;
	
	static {
		_kryo = new Kryo();
		_mapper = new ObjectMapper();
	}

	public static void initializeTree(Game game) {
		boolean allExists = false;

		for (int i = 0; i <= 9; i++) {
			final Path filePath = Paths.get(String.format(TreeUtil.TREE_FILE_TEMPLATE, i));
			allExists = Files.exists(filePath);
			if (!allExists) {
				break;
			}
		}

		if (!allExists) {
			final Path treeDirectory = Paths.get(TreeUtil.TREE_DIRECTORY);

			boolean directoryExists = true;

			if (!Files.exists(treeDirectory)) {
				try {
					Files.createDirectory(treeDirectory);
				}
				catch (final IOException e) {
					e.printStackTrace();
					directoryExists = false;
				}
			}

			if (directoryExists) {
				final TreeNode radix = new TreeNode(null, game.getBoard());
				final TreeNode radixNode = TreeUtil.generateTree(game, radix, 1);

				final Stream<TreeNode> treeStream = GameUtil.stream(radixNode);

				game.setTree(treeStream.collect(Collectors.toList()));

				GameUtil.saveTreeAtDepthToBinaryFiles(game.getTree());
			}
		}
	}

	public static void initializeTreeAllNodes(Game game) {
		boolean allExists = false;

		for (int i = 0; i <= 9; i++) {
			final Path filePath = Paths.get(TreeUtil.TREE_ALL_NODES_FILE);
			allExists = Files.exists(filePath);
			if (!allExists) {
				break;
			}
		}

		if (!allExists) {
			final Path treeDirectory = Paths.get(TreeUtil.TREE_DIRECTORY);

			boolean directoryExists = true;

			if (!Files.exists(treeDirectory)) {
				try {
					Files.createDirectory(treeDirectory);
				}
				catch (final IOException e) {
					e.printStackTrace();
					directoryExists = false;
				}
			}

			if (directoryExists) {
				final TreeNode radix = new TreeNode(null, game.getBoard());
				final TreeNode radixNode = TreeUtil.generateTree(game, radix, 1);

				final Stream<TreeNode> treeStream = GameUtil.stream(radixNode);

				game.setTree(treeStream.collect(Collectors.toList()));

				GameUtil.saveTreeToBinaryFile(game.getTree());
			}
		}
	}
	
	public static List<TreeNode> readTreeAtDepthAsJSONFromFile(int depth) {
		List<TreeNode> tree = null;

		final Path path = Paths.get(String.format(TreeUtil.TREE_FILE_TEMPLATE, depth));
		try {
			final String lines = Files.readAllLines(path).stream().reduce("", (x, y) -> x + y);
			try {
				tree = _mapper.readValue(lines, new TypeReference<List<TreeNode>>() {
				});
			}
			catch (final JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		catch (final FileNotFoundException e1) {
			e1.printStackTrace();
		}
		catch (final IOException e1) {
			e1.printStackTrace();
		}

		return tree;
	}
	
	@SuppressWarnings("unchecked")
	public static List<TreeNode> readTreeAtDepthFromBinaryFile(int depth) {
		List<TreeNode> tree = null;
		
		final Path path = Paths.get(String.format(TreeUtil.TREE_FILE_TEMPLATE, depth));
		try {
			final Input input = new Input(new FileInputStream(path.toFile()));
			tree = _kryo.readObject(input, ArrayList.class);
		}
		catch (final FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		return tree;
	}

	@SuppressWarnings("unchecked")
	public static List<TreeNode> readTreeFromBinaryFile() {
		List<TreeNode> tree = null;
		
		final Path path = Paths.get(TreeUtil.TREE_ALL_NODES_FILE);
		try {
			final Input input = new Input(new FileInputStream(path.toFile()));
			tree = _kryo.readObject(input, ArrayList.class);
		}
		catch (final FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		return tree;
	}

	public static void saveTreeAtDepthAsJSONToFiles(List<TreeNode> tree) {
		final Map<Long, List<TreeNode>> depthSubTree = new TreeMap<>();
		tree.forEach(treeNode -> {
			if (!depthSubTree.containsKey(treeNode.getDepth())) {
				final List<TreeNode> nodes = new ArrayList<>();
				depthSubTree.put(treeNode.getDepth(), nodes);
			}
			depthSubTree.get(treeNode.getDepth()).add(treeNode);
		});
		depthSubTree.keySet().forEach((key) -> {
			final String fileName = String.format(TreeUtil.TREE_FILE_TEMPLATE, key);
			final Path path = Paths.get(fileName);
			final ObjectWriter writer = _mapper.writer();
			try {
				final byte[] result = writer.writeValueAsBytes(depthSubTree.get(key));
				Files.write(path, result);
			}
			catch (final JsonProcessingException e) {
				e.printStackTrace();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static void saveTreeAtDepthToBinaryFiles(List<TreeNode> tree) {
		final Map<Long, List<TreeNode>> depthSubTree = new TreeMap<>();
		tree.forEach(treeNode -> {
			if (!depthSubTree.containsKey(treeNode.getDepth())) {
				final List<TreeNode> nodes = new ArrayList<>();
				depthSubTree.put(treeNode.getDepth(), nodes);
			}
			depthSubTree.get(treeNode.getDepth()).add(treeNode);
		});
		depthSubTree.keySet().forEach((key) -> {
			try {
				final String fileName = String.format(TreeUtil.TREE_FILE_TEMPLATE, key);
				final Output output = new Output(new FileOutputStream(fileName));
				_kryo.writeObject(output, depthSubTree.get(key));
				output.flush();
				output.close();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static void saveTreeToBinaryFile(List<TreeNode> tree) {
		try {
			final String fileName = TreeUtil.TREE_ALL_NODES_FILE;
			final Output output = new Output(new FileOutputStream(fileName));
			_kryo.writeObject(output, tree);
			output.flush();
			output.close();
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static Stream<TreeNode> stream(TreeNode parentNode) {
		if (parentNode.isLeaf()) {
			return Stream.of(parentNode);
		}
		else {
			return parentNode.getChildren().stream().map(childNode -> {
				return GameUtil.stream(childNode);
			}).reduce(Stream.of(parentNode), (s1, s2) -> Stream.concat(s1, s2));
		}
	}

	private GameUtil() {
		throw new AssertionError();
	}

}
