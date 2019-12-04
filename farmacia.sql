/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

create database farmacia;
use farmacia;

CREATE TABLE IF NOT EXISTS usuario(
	idUsuario int(11) NOT NULL AUTO_INCREMENT,
	nomeUsuario varchar(200) NOT NULL,
	tipoUsuario enum('A','G') not null,
	loginUsuario varchar(100) NOT NULL,
	senhaUsuario varchar(20) NOT NULL,
	PRIMARY KEY (idUsuario)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 ;

INSERT INTO usuario(nomeUsuario,tipoUsuario,loginUsuario,senhaUsuario) VALUES ('Atendente Ariadne','A','ariadne','123');
INSERT INTO usuario(nomeUsuario,tipoUsuario,loginUsuario,senhaUsuario) VALUES ('Gerente','G','gerente','123');

CREATE TABLE IF NOT EXISTS cliente(
	cpfCliente varchar(11) NOT NULL unique ,
	nomeCLiente varchar(200) NOT NULL,
	rgCLiente varchar(9) NOT NULL ,	
	enderecoCliente varchar(300) NOT NULL,
	telefoneCLiente varchar(11) NOT NULL,
	tipoCLiente enum('E','N') not null,
	statusCliente boolean NOT NULL,
	PRIMARY KEY (cpfCliente)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 ;

CREATE TABLE IF NOT EXISTS clienteEspecial(
	cpfCliente varchar(11) NOT NULL ,
	qtdDEsconto int(2) NOT NULL,
	PRIMARY KEY (cpfCliente),
	foreign key(cpfCliente) references cliente(cpfCliente)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;	

CREATE TABLE IF NOT EXISTS clienteNormal(
	cpfCliente varchar(11) NOT NULL ,
	descontoDinheiro int(9) NOT NULL,
	PRIMARY KEY (cpfCliente),
	foreign key(cpfCliente) references cliente(cpfCliente)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;	

INSERT INTO cliente(cpfCliente,nomeCLiente,rgCLiente,enderecoCliente,telefoneCLiente,tipoCLiente,statusCliente) VALUES('81800667205','Adao Souza','172891735','Avenida das Gardenias 1134','16348169198','E', true);	
INSERT INTO clienteEspecial(cpfCliente,qtdDEsconto)VALUES('81800667205',20);
INSERT INTO cliente(cpfCliente,nomeCLiente,rgCLiente,enderecoCliente,telefoneCLiente,tipoCLiente,statusCliente) VALUES('15185358793','Sofia Pinto','982789217','Travessa Jose da Silva Leonardo 926','21419776738','E',true);
INSERT INTO clienteEspecial(cpfCliente,qtdDEsconto)VALUES('15185358793',20);
INSERT INTO cliente(cpfCliente,nomeCLiente,rgCLiente,enderecoCliente,telefoneCLiente,tipoCLiente,statusCliente) VALUES('96194285958','Arthur Martins','167892367','Avenida Decolores 1155','63405839217','N',true);	
INSERT INTO clienteNormal(cpfCliente,descontoDinheiro)VALUES('96194285958',5);
INSERT INTO cliente(cpfCliente,nomeCLiente,rgCLiente,enderecoCliente,telefoneCLiente,tipoCLiente,statusCliente) VALUES('59913550610','Nicole Goncalves','657930281','Rua Antonio Correia de Lemos 1424','11216788581','N',true);		
INSERT INTO clienteNormal(cpfCliente,descontoDinheiro)VALUES('59913550610',5);

CREATE TABLE IF NOT EXISTS caixaDisponivel(
	idCaixaDisponivel int(11) NOT NULL,
	utilizando boolean NOT NULL, 
	PRIMARY KEY (idCaixaDisponivel)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;	

INSERT INTO caixaDisponivel(idCaixaDisponivel,utilizando)VALUES(1,false);
INSERT INTO caixaDisponivel(idCaixaDisponivel,utilizando)VALUES(2,false);
INSERT INTO caixaDisponivel(idCaixaDisponivel,utilizando)VALUES(3,false);

CREATE TABLE IF NOT EXISTS caixa(
	idCaixa int(11) NOT NULL AUTO_INCREMENT,
	idCaixaDisponivel int(11) NOT NULL,
	dataCaixa date NOT NULL,
	horaCaixa time NOT NULL,
	valorInicial decimal(8,2) NOT NULL,
	valorCartao decimal(8,2) NOT NULL,
	valorDinheiro decimal(8,2) NOT NULL,
	valorCaixaFechado decimal(8,2) NOT NULL,
	status boolean NOT NULL,
	PRIMARY KEY (idCaixa),
	foreign key(idCaixaDisponivel) references caixaDisponivel(idCaixaDisponivel)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;	

INSERT INTO caixa(idCaixa,idCaixaDisponivel,dataCaixa,horaCaixa,valorInicial,valorCartao,valorDinheiro,status) VALUES(1,1,STR_TO_DATE( "01/11/2019", "%d/%m/%Y" ),'12:05:39',250.8,10.5,20.5,true);
UPDATE caixaDisponivel SET utilizando = true WHERE idCaixaDisponivel = 1;
INSERT INTO caixa(idCaixa,idCaixaDisponivel,dataCaixa,horaCaixa,valorInicial,valorCartao,valorDinheiro,status) VALUES(2,2,STR_TO_DATE( "01/11/2019", "%d/%m/%Y" ),'12:07:39',200.8,5.5,7.5,true);
UPDATE caixaDisponivel SET utilizando = true WHERE idCaixaDisponivel = 2;

CREATE TABLE IF NOT EXISTS estoque(
	lote int(11) NOT NULL AUTO_INCREMENT,
	quantidade int(3) NOT NULL,
	distribuidor varchar(200)NOT NULL,
	PRIMARY KEY (lote)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;	

INSERT INTO estoque(lote,quantidade,distribuidor)VALUES(1,999,'MedSul Pharma');
INSERT INTO estoque(lote,quantidade,distribuidor)VALUES(2,999,'Neosul');
INSERT INTO estoque(lote,quantidade,distribuidor)VALUES(3,999,'Potiguar Farmaceutica');
INSERT INTO estoque(lote,quantidade,distribuidor)VALUES(4,999,'MedSul Pharma');
INSERT INTO estoque(lote,quantidade,distribuidor)VALUES(5,999,'MedSul Pharma');

CREATE TABLE IF NOT EXISTS medicamento(
	idMedicamento int(11) NOT NULL AUTO_INCREMENT,
	lote int(11) NOT NULL,
	nomeMedicamento varchar(200)NOT NULL,
	descricaoMedicamento varchar(200)NOT NULL,
	precoMedicamento decimal(8,2) NOT NULL,
	validadeMedicamento date NOT NULL,
	statusMedicamento boolean NOT NULL,
	PRIMARY KEY (idMedicamento),
	foreign key(lote) references estoque(lote)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

INSERT INTO medicamento(idMedicamento,lote,nomeMedicamento,descricaoMedicamento,precoMedicamento,validadeMedicamento,statusMedicamento)
VALUES(1,1,'Paracetamol','750mg Com 20 Comprimidos Genericos Ems','6.25',STR_TO_DATE( "01/01/2020", "%d/%m/%Y" ),true);

INSERT INTO medicamento(idMedicamento,lote,nomeMedicamento,descricaoMedicamento,precoMedicamento,validadeMedicamento,statusMedicamento)
VALUES(2,2,'Amoxilina Generico','875mg + Clavulanato de Potassio 125mg','80.75',STR_TO_DATE( "01/11/2019", "%d/%m/%Y" ),true);

INSERT INTO medicamento(idMedicamento,lote,nomeMedicamento,descricaoMedicamento,precoMedicamento,validadeMedicamento,statusMedicamento)
VALUES(3,3,'Novalgina','Analgesico Novalgina 1g 10 Cpr','15.25',STR_TO_DATE( "02/02/2020", "%d/%m/%Y" ),true);

INSERT INTO medicamento(idMedicamento,lote,nomeMedicamento,descricaoMedicamento,precoMedicamento,validadeMedicamento,statusMedicamento)
VALUES(4,4,'Aspirina','Aspirina Prevent 100mg Com 30 Comprimidos','14.25',STR_TO_DATE( "02/06/2020", "%d/%m/%Y" ),true);

INSERT INTO medicamento(idMedicamento,lote,nomeMedicamento,descricaoMedicamento,precoMedicamento,validadeMedicamento,statusMedicamento)
VALUES(5,5,'Advil','Advil 400mg Com 8 Cápsulas','13.40',STR_TO_DATE( "02/05/2020", "%d/%m/%Y" ),true);


CREATE TABLE IF NOT EXISTS venda(
	idVenda int(11) NOT NULL AUTO_INCREMENT,
	idCaixa int(11) NOT NULL,
	cpfCliente varchar(11) NOT NULL,
	numeroNotaFiscal varchar(44) NOT NULL unique,
	dataVenda date NOT NULL,
	horaVenda time NOT NULL,
	valorTotal decimal(8,2) NOT NULL,
	tipoPagamento enum('D','C') not null,
	compraAtiva boolean NOT NULL,
	PRIMARY KEY (idVenda),
	foreign key(idCaixa) references caixa(idCaixa),
	foreign key(cpfCliente) references cliente(cpfCliente)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

INSERT INTO venda(idVenda,idCaixa,cpfCliente,numeroNotaFiscal,dataVenda,horaVenda,valorTotal,tipoPagamento,compraAtiva)
VALUES(1,1,'15185358793','25559474260011696459913866992462785539754132',CURDATE(),CURTIME(),182.24,'C',1);
UPDATE caixa SET valorCartao=valorCartao+182.24 WHERE idCaixa=1;

INSERT INTO venda(idVenda,idCaixa,cpfCliente,numeroNotaFiscal,dataVenda,horaVenda,valorTotal,tipoPagamento,compraAtiva)
VALUES(2,1,'59913550610','13856690362490019965441238782773523952945999',CURDATE(),CURTIME(),27.08,'D',1);
UPDATE caixa SET valorCartao=valorCartao+27.08 WHERE idCaixa=1;

CREATE TABLE IF NOT EXISTS medicamentoVenda(
	idMedicamento int(11) NOT NULL,
	idVenda int(11) NOT NULL,
	quantidadeVendida int(11) NOT NULL,
	PRIMARY KEY (idMedicamento,idVenda),
	foreign key(idMedicamento) references medicamento(idMedicamento),
	foreign key(idVenda) references venda(idVenda)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;	

INSERT INTO medicamentoVenda(idMedicamento,idVenda,quantidadeVendida)VALUES(5,1,17);
UPDATE estoque SET quantidade=quantidade-17 WHERE lote=5;

INSERT INTO medicamentoVenda(idMedicamento,idVenda,quantidadeVendida)VALUES(4,2,2);
UPDATE estoque SET quantidade=quantidade-2 WHERE lote=2;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;