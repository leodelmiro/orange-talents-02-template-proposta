INSERT INTO tb_proposals(id, document, email, name, address, number, cep, salary, status)
VALUES (0, '45523947032', 'teste@teste.com', 'Testador', 'Rua dos Testes', '30', '12345-000', '2000', 1);

INSERT INTO tb_cards(id, card_number, holder, card_limit, created_at, proposal_id)
VALUES (0, '5209-1622-1164-3586', 'Testador', 1000, '2021-03-10T08:47:08.644', 0);