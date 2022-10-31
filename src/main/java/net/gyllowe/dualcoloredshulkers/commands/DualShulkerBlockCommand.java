package net.gyllowe.dualcoloredshulkers.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.gyllowe.dualcoloredshulkers.interfaces.DualColoredShulkerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Objects;

public class DualShulkerBlockCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
		dispatcher.register(
				CommandManager.literal("dualshulker")
						.then(CommandManager.literal("block")
								.then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())

										// <color>/blank <shulkerpart>
										.then(CommandManager.argument("dyecolor", DyeColorArgumentType.dyeColor())
												.then(CommandManager.argument("shulkerpart", ShulkerPartArgumentType.shulkerPart())
														.executes(
																context -> DualShulkerBlockCommand.ExecuteColor(
																		context.getSource(),
																		BlockPosArgumentType.getBlockPos(
																				context,
																				"pos"
																		),
																		DyeColorArgumentType.getDyeColor(
																				context,
																				"dyecolor"
																		),
																		ShulkerPartArgumentType.getShulkerPart(
																				context,
																				"shulkerpart"
																		)
																)
														)
												)
										)
										.then(CommandManager.literal("blank")
												.then(CommandManager.argument("shulkerpart", ShulkerPartArgumentType.shulkerPart())
														.executes(
																context -> DualShulkerBlockCommand.ExecuteColor(
																		context.getSource(),
																		BlockPosArgumentType.getBlockPos(
																				context,
																				"pos"
																		),
																		null,
																		ShulkerPartArgumentType.getShulkerPart(
																				context,
																				"shulkerpart"
																		)
																)
														)
												)
										)

										// query <shulkerpart>
										.then(CommandManager.literal("query")
												.then(CommandManager.argument("shulkerpart", ShulkerPartArgumentType.shulkerPart())
														.executes(
																context -> DualShulkerBlockCommand.ExecuteQuery(
																		context.getSource(),
																		BlockPosArgumentType.getBlockPos(
																				context,
																				"pos"
																		),
																		ShulkerPartArgumentType.getShulkerPart(
																				context,
																				"shulkerpart"
																		)
																)
														)
												)
										)

										// removebasecolor
										.then(CommandManager.literal("removebasecolor")
												.executes(
														context -> DualShulkerBlockCommand.ExecuteRemoveBaseColor(
																context.getSource(),
																BlockPosArgumentType.getBlockPos(
																		context,
																		"pos"
																)
														)
												)
										)
								)
						)
		);
		//			/dualshulker block ~ ~-1 ~ white lid
		//			/dualshulker block ~ ~-1 ~ query both

		//			/dualshulker block <pos> <color> ( lid | base | both )
		//			/dualshulker block <pos> blank ( lid | base | both )
		//			/dualshulker block <pos> query ( lid | base | both )
		//			/dualshulker block <pos> removebasecolor
	}
	//			/dualshulker block ~ ~-1 ~ white lid
	//			/dualshulker block ~ ~-1 ~ query both

	//			/dualshulker block <pos> <color> ( lid | base | both )
	//			/dualshulker block <pos> blank ( lid | base | both )
	//			/dualshulker block <pos> query ( lid | base | both )
	//			/dualshulker block <pos> removebasecolor

	private static int execute(ServerCommandSource source, BlockPos pos, CommandMode mode, @Nullable DyeColor color, ShulkerPart shulkerPart) {
		ServerWorld world = source.getWorld();

		BlockEntity BE = world.getBlockEntity(pos);
		if( !(BE instanceof ShulkerBoxBlockEntity ShulkerBE) ) {
			// no shulker box found at position x y z
			source.sendFeedback(Text.of("No shulker box found"), false);
			return -1;
		}

		DualColoredShulkerBlockEntity DualShulkerBE = (DualColoredShulkerBlockEntity) ShulkerBE;

		StringBuilder feedbackBuilder = new StringBuilder();

		if(mode == CommandMode.Color) {
			if(shulkerPart != ShulkerPart.BASE) {
				// TODO: color lid of shulker box block with command (DualShulkerBlockCommand)
				source.sendFeedback(Text.of("Coloring lids isn't implemented yet!"), false);
				return 0;
			}
			if(shulkerPart != ShulkerPart.LID) {
				if(color == ShulkerBE.getColor()) {
					DualShulkerBE.RemoveSecondaryColor();
				} else {
					DualShulkerBE.SetSecondaryColor(true, color);
				}

			}

			// ( "Lid" | "Base" | "Lid and base" )" of shulker at"( x y z)" colored "(color)
			feedbackBuilder.append(
					(shulkerPart == ShulkerPart.LID) ?
							"Lid" :
							(shulkerPart == ShulkerPart.BASE) ?
									"Base" :
									"Lid and base"
			);
			feedbackBuilder.append(" of shulker box at")
					.append(AppendXYZ(pos))
					.append(" colored ")
					.append( (color == null) ? "blank" : color );

		} else if(mode == CommandMode.Query) {
			// TODO: fix base color returning null when shulker box doesn't have a secondary color (DualShulkerBlockCommand query)
			String lidColor = "";
			String baseColor = "";
			if(shulkerPart != ShulkerPart.BASE) {
				lidColor = (ShulkerBE.getColor() == null) ? "blank" : ShulkerBE.getColor().asString();
			}
			if(shulkerPart != ShulkerPart.LID) {
				baseColor = (DualShulkerBE.GetSecondaryColor() == null) ? "blank" : DualShulkerBE.GetSecondaryColor().asString();
			}

			feedbackBuilder.append("Shulker box at")
					.append(AppendXYZ(pos))
					.append(" has ");

			if(shulkerPart == ShulkerPart.BOTH && Objects.equals(lidColor, baseColor)) {
				feedbackBuilder.append("lid and base color ")
						.append(lidColor);
			} else {
				if(shulkerPart != ShulkerPart.BASE) {
					feedbackBuilder.append("lid color ")
							.append(lidColor);
				}
				if(shulkerPart == ShulkerPart.BOTH) {
					feedbackBuilder.append(" and ");
				}
				if(shulkerPart != ShulkerPart.LID) {
					feedbackBuilder.append("base color ")
							.append(baseColor);
				}
			}

		} else if(mode == CommandMode.RemoveBaseColor) {
			DualShulkerBE.RemoveSecondaryColor();

			feedbackBuilder.append("Base of shulker box at")
					.append(AppendXYZ(pos))
					.append(" colored to lid color (")
					.append(ShulkerBE.getColor())
					.append(")");
		}

		source.sendFeedback(Text.of( feedbackBuilder.toString() ), false);
		return 1;
		/*
		x		get block
		x		get --shulker box block entity-- from block
		|	x		if cannot, make problem

		_		dyecolor and blank:
		|	_		if not base: (lid or both)
		|	|	_		color top
		|	x		if not lid: (base or both)
		|	|	x		color base
		|	x		write feedback

		x		query:
		|	x		get color(s) of --part(s) chosen-- from shulker box
		|	x		write feedback

		x		removebasecolor:
		|	x		remove base color of shulker box
		|	x		write feedback

		x		send feedback
		 */
	}

	private static StringBuilder AppendXYZ(BlockPos pos) {
		StringBuilder builder = new StringBuilder();
		builder.append(" ")
				.append(pos.getX())
				.append(" ")
				.append(pos.getY())
				.append(" ")
				.append(pos.getZ());
		return builder;
	}

	private static int ExecuteColor(ServerCommandSource source, BlockPos pos, DyeColor color, ShulkerPart shulkerPart) {
		return execute(source, pos, CommandMode.Color, color, shulkerPart);
	}

	private static int ExecuteQuery(ServerCommandSource source, BlockPos pos, ShulkerPart shulkerPart) {
		return execute(source, pos, CommandMode.Query, null, shulkerPart);
	}

	private static int ExecuteRemoveBaseColor(ServerCommandSource source, BlockPos pos) {
		return execute(source, pos, CommandMode.RemoveBaseColor, null, ShulkerPart.BASE);
	}


	private enum CommandMode {
		Color,
		Query,
		RemoveBaseColor
	}
}
